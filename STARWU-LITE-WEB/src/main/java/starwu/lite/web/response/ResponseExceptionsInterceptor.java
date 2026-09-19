package starwu.lite.web.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.exception.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import starwu.lite.web.log.interceptor.RequestLogInterceptor;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseExceptionsInterceptor {

	private final RequestLogInterceptor requestLogInterceptor;

	/**
	 * 
	 * @Title: bindException
	 * @Description: SESSION过期异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(SessionTimeOutException.class)
	public ResponseBean bindException(HttpServletRequest request, SessionTimeOutException ex) {
		print(request, ex);
		return ResponseBean.error(ErrorCodeType.SESSION_TIMEOUT_EXCEPTION, ex.getMessage());
	}

	/**
	 * 
	 * @Title: bindException
	 * @Description: 参数校验异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResponseBean bindException(HttpServletRequest request, BindException ex) {
		print(request, ex);
		String fullMmessage = ex.getMessage();
		int begin = fullMmessage.lastIndexOf("[") + 1;
		int end = fullMmessage.length() - 1;
		String responseMessahe = ex.getMessage().substring(begin, end);
		return ResponseBean.error(ErrorCodeType.PARAM_VALIDATION_EXCEPTION, responseMessahe);
	}
	
	/**
	 * 
	 * @Title: paramValidationException
	 * @Description: 参数校验异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ParamValidationException.class)
	public ResponseBean paramValidationException(HttpServletRequest request, ParamValidationException ex) {
		print(request, ex);
		return ResponseBean.error(ErrorCodeType.PARAM_VALIDATION_EXCEPTION, ex.getMessage());
	}

	/**
	 * 
	 * @Title: permissionValidationException
	 * @Description: 权限校验异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(PermissionValidationException.class)
	public ResponseBean permissionValidationException(HttpServletRequest request, PermissionValidationException ex) {
		print(request, ex);
		return ResponseBean.error(ErrorCodeType.PARAM_VALIDATION_EXCEPTION, ex.getMessage());
	}

	/**
	 * 
	 * @Title: sqlExceptionHandler
	 * @Description: 数据库异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(SqlException.class)
	public ResponseBean sqlExceptionHandler(HttpServletRequest request, SqlException ex) {
		print(request, ex);
		return ResponseBean.error(ErrorCodeType.SYSTEM_EXCEPTION, "系统异常,请稍后重试");
	}

	/**
	 *
	 * @Title: sqlExceptionnHandler
	 * @Description: 404异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseBean noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) throws Exception {
		print(request, ex);
		requestLogInterceptor.preHandle(request,null,null);
		return ResponseBean.error(ErrorCodeType.NO_HANDLER_FOUND_EXCEPTION, "找不到该接口");
	}

	/**
	 * 
	 * @Title: businessExceptionnHandler
	 * @Description: 业务异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseBean businessExceptionnHandler(HttpServletRequest request, BusinessException ex) {
		print(request, ex);
		return ResponseBean.error(ErrorCodeType.BUSINESS_EXCEPTION, ex.getMessage());
	}

	/**
	 * 
	 * @Title: throwableExceptionHandler
	 * @Description: 系统异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseBean throwableExceptionHandler(HttpServletRequest request, Exception ex) {
		print(request, ex);
		return ResponseBean.error(ErrorCodeType.SYSTEM_EXCEPTION, "系统异常,请稍后重试");
	}

	/**
	 * 
	 * @Title: print
	 * @Description: 打印堆栈信息
	 * @param request
	 * @param exception
	 */
	private void print(HttpServletRequest request, Exception exception) {
		// 换行符
		String lineSeparatorStr = System.getProperty("line.separator");
		StringBuilder exStr = new StringBuilder();
		StackTraceElement[] trace = exception.getStackTrace();
		// 获取堆栈信息并输出为打印的形式
		for (StackTraceElement s : trace) {
			exStr.append("\tat " + s + "\r\n");
		}
		// 打印error级别的堆栈日志
		log.error(
				"访问地址：" + request.getRequestURL() + ",请求方法：" + request.getMethod() + ",远程地址：" + request.getRemoteAddr()
						+ lineSeparatorStr + "错误堆栈信息如下:" + exception.toString() + lineSeparatorStr + exStr);
	}
}
