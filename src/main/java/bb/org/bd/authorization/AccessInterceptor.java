package bb.org.bd.authorization;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AccessInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		Calendar cal = Calendar.getInstance(); 
			
			int dayOfWeek = cal.get(cal.DAY_OF_WEEK);  //getting the day on which request is made
			
			if(dayOfWeek == 3) { // 1 means Sunday, 2 means Monday....7 means Saturday
				
				//response.getWriter().write("The Website is closed on Wednesday; please try accessing it on any other week day!!");
				System.out.println("I will do some validation before you call my ws-lcFromBB web Service");
				//return false;
			}
			
			return true;

	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// this method would be called after Spring MVC executes the request handler method for the request
		System.out.println("HandlerInterceptorAdapter : Spring MVC called postHandle method for "
															+ request.getRequestURI().toString() );

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	
		// this method would be called after the response object is produced by the view for the request
		System.out.println("HandlerInterceptorAdapter : Spring MVC called afterCompletion method for " 
															+ request.getRequestURI().toString());
	
	}

}
