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
package caraniel.mplot.core.service;

import caraniel.mplot.core.bean.PlotBean;
import caraniel.mplot.core.bean.PlotInfosBean;
import caraniel.mplot.core.worker.PlotWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlotService
{
  private SettingsService settingsService;

  private PlotWorker plotWorker;

  public PlotService(@Autowired SettingsService settingsService, @Autowired PlotWorker plotWorker)
  {
    this.settingsService = settingsService;
    this.plotWorker = plotWorker;

    this.plotWorker.initPlotManager(settingsService.getMPlotConfig());
  }

  public PlotInfosBean getPlotInfos()
  {
    return plotWorker.getPlotInfos();
  }

  public void startPlot(PlotBean plotBean)
  {
    plotWorker.startPlot(plotBean);
  }
}
