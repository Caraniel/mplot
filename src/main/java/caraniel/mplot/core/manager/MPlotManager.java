/*
 * MIT License
 *
 * Copyright (c) 2021 Caraniel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package caraniel.mplot.core.manager;

import caraniel.mplot.core.config.MPlotConfigBean;
import caraniel.mplot.core.job.MPlotJob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Logger;

public class MPlotManager
{
  private static final Logger LOG = Logger.getLogger(MPlotManager.class.getName());

  public static final String NO_GROUP_KEY = "NO_GROUP_KEY";

  private ExecutorService executorService;
  private Map<String, MPlotJobBean> mPlotJobLookup = Collections.synchronizedMap(new HashMap<>());

  private Set<String> activeGroups = Collections.synchronizedSet(new HashSet<>());
  private Map<String, List<MPlotJobBean>> groupQueue = Collections.synchronizedMap(new HashMap<>());

  private final Object SYNC_GROUPS = new Object();

  public MPlotManager()
  {

  }

  public void initManager(MPlotConfigBean mPlotConfig)
  {
    LOG.info("Init MPlotManager!");

    LOG.info("threadAmount: " + mPlotConfig.getThreadAmount());
    executorService = Executors.newFixedThreadPool(mPlotConfig.getThreadAmount());
  }

  public String start(MPlotJob mPlotJob, StateCallback stateCallback)
  {
    MPlotJobBean mPlotJobBean = new MPlotJobBean(mPlotJob, stateCallback);
    mPlotJobLookup.put(mPlotJobBean.getUuid(), mPlotJobBean);

    if(isGroupAllowed(mPlotJobBean))
    {
      LOG.info("Start MPlotJob: " + mPlotJobBean.getName() + "-" + mPlotJobBean.getUuid());

      startJob(mPlotJobBean);
    }
    return mPlotJobBean.getUuid();
  }

  private boolean isGroupAllowed(MPlotJobBean mPlotJobBean)
  {
    synchronized(SYNC_GROUPS)
    {
      boolean groupAllowed;
      MPlotJob mPlotJob = mPlotJobBean.getMPlotJob();
      if(NO_GROUP_KEY.equals(mPlotJob.getGroupKey()))
      {
        groupAllowed = true;
      }
      else
      {
        String groupKey = mPlotJob.getGroupKey();
        if(activeGroups.contains(groupKey))
        {
          groupAllowed = false;
          groupQueue.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(mPlotJobBean);
        }
        else
        {
          activeGroups.add(groupKey);
          groupAllowed = true;
        }
      }
      return groupAllowed;
    }
  }

  private void startJob(MPlotJobBean mPlotJobBean)
  {
    CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(mPlotJobBean.getMPlotJob(), executorService);
    voidCompletableFuture.thenApply(new Function<Void, Object>()
    {
      @Override
      public Object apply(Void unused)
      {
        mPlotJobBean.getFinishCallback().finished(unused);

        removeAndCheckGroups(mPlotJobBean);

        LOG.info("Start MPlotJob: " + mPlotJobBean.getName() + "-" + mPlotJobBean.getUuid() + " --- AHAH");
        return null;
      }
    });

    mPlotJobBean.setFuture(voidCompletableFuture);
  }

  private void removeAndCheckGroups(MPlotJobBean mPlotJobBean)
  {
    synchronized(SYNC_GROUPS)
    {
      String groupKey = mPlotJobBean.getGroupKey();
      if(!NO_GROUP_KEY.equals(groupKey))
      {
        List<MPlotJobBean> mPlotJobBeans = groupQueue.getOrDefault(groupKey, new ArrayList<>());

        if(!mPlotJobBeans.isEmpty())
        {
          MPlotJobBean nextMPlotJobBean = mPlotJobBeans.get(0);
          mPlotJobBeans.remove(nextMPlotJobBean);
          startJob(mPlotJobBean);
        }
        else
        {
          activeGroups.remove(groupKey);
        }
      }
    }
  }

  public List<MPlotJob> finish()
  {
    List<MPlotJob> mPlotJobs = new ArrayList<>();

    List<Runnable> runnables = executorService.shutdownNow();

    // TODO .. select unfinished mplot jobs

    LOG.info("Finish MPlotManager!");

    return mPlotJobs;
  }

  public interface StateCallback
  {
    void finished(Void unused);
  }
}
