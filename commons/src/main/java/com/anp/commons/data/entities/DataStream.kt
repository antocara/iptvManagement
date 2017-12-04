package com.anp.commons.data.entities

import java.io.InputStream


data class DataStream(var contentType: String?, var inputStream: InputStream?){

  constructor():this(null, null)

}