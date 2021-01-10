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
package caraniel.mplot.core.job;

import caraniel.mplot.core.bean.PlotBean;

public class MPlotJob
  extends ExecuteShellJob
{
  public MPlotJob(PlotBean plotBean)
  {
    super(plotBean.getTempDirectory(), plotBean.getName(), null, createCommandArray(plotBean));
  }

  private static String[] createCommandArray(PlotBean plotBean)
  {
    return new String[]{plotBean.getPlotterFile(), "plots", "create", "-k", String.valueOf(plotBean.getKSize()), "-n", String.valueOf(plotBean.getRounds()),
      "-t", plotBean.getTempDirectory(), "-d", plotBean.getDestinationDirectory()};
  }
}
