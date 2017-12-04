package com.anp.iptvmanagement.presentations.widgets

import android.content.Context
import android.support.v17.leanback.widget.BaseCardView
import android.support.v7.widget.AppCompatSeekBar
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.anp.iptvmanagement.R
import kotlinx.android.synthetic.main.epg_image_card_view.view.channel_icon
import kotlinx.android.synthetic.main.epg_image_card_view.view.epg_metadata
import kotlinx.android.synthetic.main.epg_image_card_view.view.epg_program_title
import kotlinx.android.synthetic.main.epg_image_card_view.view.seekBar_widget
import kotlinx.android.synthetic.main.epg_image_card_view.view.title_channel


class EpgImageCardView : BaseCardView {

  var mainImageView: ImageView? = null
  var titleText: TextView? = null
  var epgMetadata: View? = null
  var seekBar: AppCompatSeekBar? = null
  var epgProgramTitle: TextView? = null


  constructor(context: Context?) : this(context, null)

  constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,
      android.support.v17.leanback.R.attr.imageCardViewStyle)

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
      super(context, attrs, defStyleAttr) {


    initializeView(getImageCardViewStyle(context, attrs, defStyleAttr))
  }


  private fun initializeView( defStyle: Int) {
    isFocusable = true
    isFocusableInTouchMode = true

    cardType = BaseCardView.CARD_TYPE_MAIN_ONLY
    setBackgroundResource(R.color.color_primary_dark)


    val inflater = LayoutInflater.from(context)
    inflater.inflate(R.layout.epg_image_card_view, this)


    mainImageView = channel_icon
    titleText = title_channel
    epgMetadata = epg_metadata
    seekBar = seekBar_widget
    epgProgramTitle = epg_program_title


    val cardAttrs = context.obtainStyledAttributes(
        defStyle, android.support.v17.leanback.R.styleable.lbImageCardView)
    cardAttrs.recycle()
  }


  private fun getStyledContext(context: Context?, attrs: AttributeSet?,
      defStyleAttr: Int): Context {
    val style = getImageCardViewStyle(context, attrs, defStyleAttr)
    return ContextThemeWrapper(context, style)
  }

  private fun getImageCardViewStyle(context: Context?, attrs: AttributeSet?,
      defStyleAttr: Int): Int {
    // Read style attribute defined in XML layout.
    var style = attrs?.styleAttribute ?: 0
    if (0 == style) {
      // Not found? Read global ImageCardView style from Theme attribute.
      val styledAttrs = context?.obtainStyledAttributes(android.support.v17.leanback.R.styleable.LeanbackTheme)
      styledAttrs?.let { typedArray ->
        style = typedArray.getResourceId(android.support.v17.leanback.R.styleable.LeanbackTheme_imageCardViewStyle, 0)
      }

      styledAttrs?.recycle()
    }
    return style
  }


  /**
   * Sets the layout dimensions of the ImageView.
   */
  fun setMainImageDimensions(width: Int, height: Int) {
    val lp = mainImageView?.layoutParams
    lp?.width = width
    lp?.height = height
    mainImageView?.layoutParams = lp
  }


  override fun hasOverlappingRendering(): Boolean = false

}