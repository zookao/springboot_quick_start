package com.zookao.admin.config;

import com.zookao.admin.exception.ParamJsonException;
import com.zookao.admin.helper.ResponseHelper;
import com.zookao.admin.helper.ResponseModel;
import com.zookao.common.base.BusinessException;
import com.zookao.common.base.CodeEnum;
import com.zookao.common.util.ComUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller统一异常处理
 */
@ControllerAdvice
@Slf4j
public class AllControllerAdvice {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }


    /**
     * 捕捉shiro的异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResponseModel<String> handleShiroException(ShiroException e) {
        return ResponseHelper.failedWith(null, CodeEnum.IDENTIFICATION_ERROR.getCode(), CodeEnum.IDENTIFICATION_ERROR.getMsg());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseModel<String> handleShiroException(AuthenticationException e) {
        return ResponseHelper.failedWith(null, CodeEnum.IDENTIFICATION_ERROR.getCode(), CodeEnum.IDENTIFICATION_ERROR.getMsg());
    }

    /**
     * 捕捉BusinessException自定义抛出的异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseModel handleBusinessException(BusinessException e) {
        String message = e.getMessage();
        if (message.indexOf(":--:") > 0) {
            String[] split = message.split(":--:");
            return ResponseHelper.failedWith(null, split[1], split[0]);
        }
        return ResponseHelper.failedWith(null, CodeEnum.DATA_ERROR.getCode(), message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ParamJsonException.class)
    @ResponseBody
    public ResponseModel<String> handleParamJsonException(Exception e) {
        if (e instanceof ParamJsonException) {
            log.info("参数错误：" + e.getMessage());
            return ResponseHelper.failed2Message("参数错误：" + e.getMessage());
        }
        return ResponseHelper.failedWith(null, CodeEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseModel<String> handleParamJsonException(HttpMessageNotReadableException e) {
        if (e instanceof HttpMessageNotReadableException) {
            log.info("参数错误：" + e.getMessage());
            return ResponseHelper.failed2Message("参数错误：" + e.getMessage());
        }
        return ResponseHelper.failedWith(null, CodeEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<String> errorHandler(Exception ex) {
        ex.printStackTrace();
        log.error("接口出现严重异常：{}", ex.getMessage());
        if(ComUtil.isEmpty(ex.getMessage())){
            return ResponseHelper.failed2Message(CodeEnum.ERROR.getMsg());
        }else{
            return ResponseHelper.failed2Message(ex.getMessage());
        }
    }

}