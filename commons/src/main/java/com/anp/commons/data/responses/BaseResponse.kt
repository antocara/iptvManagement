package com.anp.commons.data.responses


open class BaseResponse(var code: Int = UNDEFINED_ERROR) {

  companion object {
    val SUCCESS = 200
    val UNDEFINED_ERROR = 1000
    val PARSER_FILE_ERROR = 2000
  }

  constructor():this(UNDEFINED_ERROR)
}