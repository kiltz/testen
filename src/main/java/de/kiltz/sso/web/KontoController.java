package de.kiltz.sso.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.kiltz.sso.model.Konto;
import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SSOValidationException;
import de.kiltz.sso.service.SsoService;

@Controller
@RequestMapping(value = "/konto")
public class KontoController {

	private final KontoService service;
	private final SsoService ssoService;

	@Autowired
	public KontoController(KontoService service, SsoService ssoService) {
		this.service = service;
		this.ssoService = ssoService;
	}

	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("konto", null);
		session.setAttribute("token", null);
		return "redirect";
	}
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String loginForm(Model model) {
		model.addAttribute("konto", new Konto());
		return "konto/login";
	}
	@RequestMapping(value = "/register.html", method = RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute("konto", new Konto());
		return "konto/register";
	}

	@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	public String login(
			@ModelAttribute("konto") Konto konto,
			Model model, HttpSession session) {
		Konto k = service.login(konto.getEmail(), konto.getPasswort());
		if (k == null) {
			model.addAttribute("errMsg", "Login nicht erfolgreich");
			return null;
		}
		String token = ssoService.createToken(k.getEmail());
		session.setAttribute("konto", k);
		session.setAttribute("token", token);
		return "redirect";
	}
	@RequestMapping(value = "/register.html", method = RequestMethod.POST)
	public String register(
			@ModelAttribute("konto") Konto konto,
			Model model) {

		try {
			Konto k = service.neu(konto);
			model.addAttribute("okMsg", "Konto wurde erstellt!");
		} catch (SSOValidationException e) {
			model.addAttribute("errMsg", e.getMessage());
		}
		return null;
	}

}
