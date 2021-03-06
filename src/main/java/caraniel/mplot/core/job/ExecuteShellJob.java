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

import java.io.File;
import java.io.IOException;

public class ExecuteShellJob
  extends AbstractJob
{
  private File outputFile;
  private String[] commands;

  public ExecuteShellJob(StateCallback stateCallback)
  {
    super("ExecuteShellMPlotJob", stateCallback);
  }

  public ExecuteShellJob(String groupKey, String name, StateCallback stateCallback, File outputFile, String... commands)
  {
    super(groupKey, name, stateCallback);
    this.outputFile = outputFile;
    this.commands = commands;
  }

  @Override
  public void runJob()
  {
    ProcessBuilder processBuilder = new ProcessBuilder(commands);

    try
    {
      if(outputFile != null)
      {
        processBuilder.redirectOutput(outputFile);
      }

      Process process = processBuilder.start();

      System.out.println("x");
      try
      {
        int i = process.waitFor();
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }

      //while(process.isAlive())
      {
        //process.destroy();
        System.out.println(".");
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }
}
