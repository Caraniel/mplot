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
package caraniel.mplot.web.controller;

import caraniel.mplot.core.bean.PlotBean;
import caraniel.mplot.core.config.MPlotConfigBean;
import caraniel.mplot.core.service.PlotService;
import caraniel.mplot.core.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MPlotController
{
  @Autowired
  private SettingsService settingsService;

  @Autowired
  private PlotService plotService;

  @GetMapping("/plotoverview")
  public String plotoverview( Model model)
  {
    model.addAttribute("plotInfos", plotService.getPlotInfos());
    model.addAttribute("mPlotBean", new PlotBean());
    return "plotoverview";
  }

  @PostMapping("plotnewentry")
  public ModelAndView plotNewEntry(@ModelAttribute(name = "mPlotBean") PlotBean plotBean, Model model)
  {
    plotService.startPlot(plotBean);
//    settingsService.storeMPlotConfig(mPlotConfig);
    return new ModelAndView("redirect:/plotoverview");
  }

  @GetMapping("/configview")
  public String configview(Model model)
  {
    model.addAttribute("mPlotConfig", settingsService.getMPlotConfig());
    return "configview";
  }

  @PostMapping("configform")
  public ModelAndView configForm(@ModelAttribute(name = "mPlotConfig") MPlotConfigBean mPlotConfig, Model model)
  {
    settingsService.storeMPlotConfig(mPlotConfig);
    return new ModelAndView("redirect:/configview");
  }
}
