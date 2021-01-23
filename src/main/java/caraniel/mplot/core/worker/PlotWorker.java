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
package caraniel.mplot.core.worker;

import caraniel.mplot.core.bean.PlotBean;
import caraniel.mplot.core.bean.PlotInfosBean;
import caraniel.mplot.core.config.MPlotConfigBean;
import caraniel.mplot.core.job.MPlotJob;
import caraniel.mplot.core.manager.MPlotManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PlotWorker
{
  private MPlotManager plotManager = new MPlotManager();

  public void initPlotManager(MPlotConfigBean mPlotConfig)
  {
    plotManager.initManager(mPlotConfig);
  }

  public void startPlot(PlotBean plotBean)
  {
    plotManager.start(new MPlotJob(plotBean), new MPlotManager.FinishCallback()
    {
      @Override
      public void finished(Void unused)
      {

      }
    });
  }

  public PlotInfosBean getPlotInfos()
  {
//    plotManager.

    return new PlotInfosBean(new ArrayList<>());
  }
}
