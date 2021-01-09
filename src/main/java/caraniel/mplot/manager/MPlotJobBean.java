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
package caraniel.mplot.manager;

import caraniel.mplot.job.MPlotJob;

import java.util.UUID;
import java.util.concurrent.Future;

public class MPlotJobBean
{
  private String uuid;
  private MPlotJob mplotJob;
  private Future<?> future;

  private MPlotManager.FinishCallback finishCallback;

  public MPlotJobBean(MPlotJob mPlotJob, MPlotManager.FinishCallback finishCallback)
  {
    this.uuid = UUID.randomUUID().toString();
    this.mplotJob = mPlotJob;
    this.finishCallback = finishCallback;
  }

  public String getUuid()
  {
    return uuid;
  }

  public MPlotJob getMPlotJob()
  {
    return mplotJob;
  }

  public MPlotManager.FinishCallback getFinishCallback()
  {
    return finishCallback;
  }

  public String getName()
  {
    return mplotJob.getName();
  }

  public String getGroupKey()
  {
    return mplotJob.getGroupKey();
  }

  void setFuture(Future<?> future)
  {
    this.future = future;
  }

  public Future<?> getFuture()
  {
    return future;
  }
}
