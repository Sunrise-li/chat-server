package com.chat.object;

/**
 * 
 * @author ldz
 *
 */
public class ResultGenerator<T> {
	private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

	public static <T> Result<T> genSuccessResult() {
		return new Result<T>().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
	}

	public static <T> Result<T> genSuccessResult(T data) {
		return new Result<T>().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
	}

	public static <T> Result<T> genFailResult(String message) {
		return new Result<T>().setCode(ResultCode.FAIL).setMessage(message);
	}
}