package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/member/chat")
public class AuthenticationFilter implements Filter {
  
    public AuthenticationFilter() {    
    }
	
	public void destroy() {	
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//로그인 여부 확인 
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		if(session.getAttribute("member") == null) {
			//원래 이동하려고 했던 페이지의 주소를 찾아오기 
			String requestURI = req.getRequestURI();
			int idx = requestURI.lastIndexOf("/");
			String command = requestURI.substring(idx + 1);
			//이동하려고 했던 페이지의 주소 저장하기 
			session.setAttribute("dest", command);
						
			HttpServletResponse res = (HttpServletResponse)response;
			res.sendRedirect("login");
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {	
	}
}
