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

import caraniel.mplot.core.config.MPlotConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SettingsWorker
{
  private static final Logger LOG = Logger.getLogger(SettingsWorker.class.getName());

  private static final String PROPERTY_THREAD_AMOUNT = "thread.amount";

  private MPlotConfigBean mPlotConfig;
  private String settingsPath;

  public SettingsWorker(@Autowired @Value("${settings.path}") String settingsPath)
  {
    this.settingsPath = settingsPath;
    getMPlotConfig();
    loadSettings();
  }

  private void loadSettings()
  {
    try
    {
      Properties properties = new Properties();
      String absolutePath = new File(settingsPath).getAbsolutePath();
      LOG.info("Read settings from: '" + absolutePath + "'.");
      properties.load(new FileInputStream(absolutePath));

      mPlotConfig.setThreadAmount(getInt(properties.getProperty(PROPERTY_THREAD_AMOUNT), mPlotConfig.getThreadAmount()));
    }
    catch(IOException e)
    {
      LOG.log(Level.WARNING, "", e);
    }
  }

  private int getInt(String propertyValue, int defaultValue)
  {
    int value;
    try
    {
      value = Integer.parseInt(propertyValue);
    }
    catch(Exception e)
    {
      value = defaultValue;
    }
    return value;
  }

  public MPlotConfigBean getMPlotConfig()
  {
    if(mPlotConfig == null)
    {
      mPlotConfig = new MPlotConfigBean(1);
    }
    return mPlotConfig;
  }

  public MPlotConfigBean storeMPlotConfig(MPlotConfigBean mPlotConfig)
  {
    try
    {
      Properties properties = new Properties();
      fillProperties(properties, this.mPlotConfig);
      fillProperties(properties, mPlotConfig);
      String absolutePath = new File(settingsPath).getAbsolutePath();
      LOG.info("Save settings to: '" + absolutePath + "'.");
      properties.store(new FileOutputStream(absolutePath), "Date: ");

      loadSettings();
    }
    catch(IOException e)
    {
      LOG.log(Level.WARNING, "", e);
    }

    return this.mPlotConfig;
  }

  private void fillProperties(Properties properties, MPlotConfigBean mPlotConfig)
  {
    properties.put(PROPERTY_THREAD_AMOUNT, String.valueOf(mPlotConfig.getThreadAmount()));
  }
}
