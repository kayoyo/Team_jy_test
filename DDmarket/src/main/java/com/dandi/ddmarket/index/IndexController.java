package com.dandi.ddmarket.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dandi.ddmarket.ViewRef;

@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping(value="/main")
	public String index(Model model, RedirectAttributes ra) {
		
		model.addAttribute("view",ViewRef.INDEX_MAIN);
		return ViewRef.DEFAULT_TEMP;
	}	
}
