package com.anp.commons.helpers

import android.graphics.Color
import java.util.Random


object HelperMaterialColor {


  var mMaterialColors = arrayListOf<String>()

  init {
    initializeArray()
  }

  private fun initializeArray() {
    mMaterialColors.add("#e51c23")
    mMaterialColors.add("#fde0dc")
    mMaterialColors.add("#f9bdbb")
    mMaterialColors.add("#f69988")
    mMaterialColors.add("#f36c60")
    mMaterialColors.add("#e84e40")
    mMaterialColors.add("#e51c23")
    mMaterialColors.add("#dd191d")
    mMaterialColors.add("#d01716")
    mMaterialColors.add("#c41411")
    mMaterialColors.add("#b0120a")
    mMaterialColors.add("#ff7997")
    mMaterialColors.add("#ff5177")
    mMaterialColors.add("#ff2d6f")
    mMaterialColors.add("#e00032")


    // Pink
    mMaterialColors.add("#e91e63");
    mMaterialColors.add("#fce4ec");
    mMaterialColors.add("#f8bbd0");
    mMaterialColors.add("#f48fb1");
    mMaterialColors.add("#f06292");
    mMaterialColors.add("#ec407a");
    mMaterialColors.add("#e91e63");
    mMaterialColors.add("#d81b60");
    mMaterialColors.add("#c2185b");
    mMaterialColors.add("#ad1457");
    mMaterialColors.add("#880e4f");
    mMaterialColors.add("#ff80ab");
    mMaterialColors.add("#ff4081");
    mMaterialColors.add("#f50057");
    mMaterialColors.add("#c51162");


    // Purple
    mMaterialColors.add("#9c27b0");
    mMaterialColors.add("#f3e5f5");
    mMaterialColors.add("#e1bee7");
    mMaterialColors.add("#ce93d8");
    mMaterialColors.add("#ba68c8");
    mMaterialColors.add("#ab47bc");
    mMaterialColors.add("#9c27b0");
    mMaterialColors.add("#8e24aa");
    mMaterialColors.add("#7b1fa2");
    mMaterialColors.add("#6a1b9a");
    mMaterialColors.add("#4a148c");
    mMaterialColors.add("#ea80fc");
    mMaterialColors.add("#e040fb");
    mMaterialColors.add("#d500f9");
    mMaterialColors.add("#aa00ff");


    // Deep Purple
    mMaterialColors.add("#673ab7");
    mMaterialColors.add("#ede7f6");
    mMaterialColors.add("#d1c4e9");
    mMaterialColors.add("#b39ddb");
    mMaterialColors.add("#9575cd");
    mMaterialColors.add("#7e57c2");
    mMaterialColors.add("#673ab7");
    mMaterialColors.add("#5e35b1");
    mMaterialColors.add("#512da8");
    mMaterialColors.add("#4527a0");
    mMaterialColors.add("#311b92");
    mMaterialColors.add("#b388ff");
    mMaterialColors.add("#7c4dff");
    mMaterialColors.add("#651fff");
    mMaterialColors.add("#6200ea");


    // Indigo
    mMaterialColors.add("#3f51b5");
    mMaterialColors.add("#e8eaf6");
    mMaterialColors.add("#c5cae9");
    mMaterialColors.add("#9fa8da");
    mMaterialColors.add("#7986cb");
    mMaterialColors.add("#5c6bc0");
    mMaterialColors.add("#3f51b5");
    mMaterialColors.add("#3949ab");
    mMaterialColors.add("#303f9f");
    mMaterialColors.add("#283593");
    mMaterialColors.add("#1a237e");
    mMaterialColors.add("#8c9eff");
    mMaterialColors.add("#536dfe");
    mMaterialColors.add("#3d5afe");
    mMaterialColors.add("#304ffe");

    // Blue
    mMaterialColors.add("#5677fc");
    mMaterialColors.add("#e7e9fd");
    mMaterialColors.add("#d0d9ff");
    mMaterialColors.add("#afbfff");
    mMaterialColors.add("#91a7ff");
    mMaterialColors.add("#738ffe");
    mMaterialColors.add("#5677fc");
    mMaterialColors.add("#4e6cef");
    mMaterialColors.add("#455ede");
    mMaterialColors.add("#3b50ce");
    mMaterialColors.add("#2a36b1");
    mMaterialColors.add("#a6baff");
    mMaterialColors.add("#6889ff");
    mMaterialColors.add("#4d73ff");
    mMaterialColors.add("#4d69ff");

    // Light Blue
    mMaterialColors.add("#03a9f4");
    mMaterialColors.add("#e1f5fe");
    mMaterialColors.add("#b3e5fc");
    mMaterialColors.add("#81d4fa");
    mMaterialColors.add("#4fc3f7");
    mMaterialColors.add("#29b6f6");
    mMaterialColors.add("#03a9f4");
    mMaterialColors.add("#039be5");
    mMaterialColors.add("#0288d1");
    mMaterialColors.add("#0277bd");
    mMaterialColors.add("#01579b");
    mMaterialColors.add("#80d8ff");
    mMaterialColors.add("#40c4ff");
    mMaterialColors.add("#00b0ff");
    mMaterialColors.add("#0091ea");

    // Cyan
    mMaterialColors.add("#00bcd4");
    mMaterialColors.add("#e0f7fa");
    mMaterialColors.add("#b2ebf2");
    mMaterialColors.add("#80deea");
    mMaterialColors.add("#4dd0e1");
    mMaterialColors.add("#26c6da");
    mMaterialColors.add("#00bcd4");
    mMaterialColors.add("#00acc1");
    mMaterialColors.add("#0097a7");
    mMaterialColors.add("#00838f");
    mMaterialColors.add("#006064");
    mMaterialColors.add("#84ffff");
    mMaterialColors.add("#18ffff");
    mMaterialColors.add("#00e5ff");
    mMaterialColors.add("#00b8d4");

    // Teal
    mMaterialColors.add("#009688");
    mMaterialColors.add("#e0f2f1");
    mMaterialColors.add("#b2dfdb");
    mMaterialColors.add("#80cbc4");
    mMaterialColors.add("#4db6ac");
    mMaterialColors.add("#26a69a");
    mMaterialColors.add("#009688");
    mMaterialColors.add("#00897b");
    mMaterialColors.add("#00796b");
    mMaterialColors.add("#00695c");
    mMaterialColors.add("#004d40");
    mMaterialColors.add("#a7ffeb");
    mMaterialColors.add("#64ffda");
    mMaterialColors.add("#1de9b6");
    mMaterialColors.add("#00bfa5");

    // Green
    mMaterialColors.add("#259b24");
    mMaterialColors.add("#d0f8ce");
    mMaterialColors.add("#a3e9a4");
    mMaterialColors.add("#72d572");
    mMaterialColors.add("#42bd41");
    mMaterialColors.add("#2baf2b");
    mMaterialColors.add("#259b24");
    mMaterialColors.add("#0a8f08");
    mMaterialColors.add("#0a7e07");
    mMaterialColors.add("#056f00");
    mMaterialColors.add("#0d5302");
    mMaterialColors.add("#a2f78d");
    mMaterialColors.add("#5af158");
    mMaterialColors.add("#14e715");
    mMaterialColors.add("#12c700");

    // Light Green
    mMaterialColors.add("#8bc34a");
    mMaterialColors.add("#f1f8e9");
    mMaterialColors.add("#dcedc8");
    mMaterialColors.add("#c5e1a5");
    mMaterialColors.add("#aed581");
    mMaterialColors.add("#9ccc65");
    mMaterialColors.add("#8bc34a");
    mMaterialColors.add("#7cb342");
    mMaterialColors.add("#689f38");
    mMaterialColors.add("#558b2f");
    mMaterialColors.add("#33691e");
    mMaterialColors.add("#ccff90");
    mMaterialColors.add("#b2ff59");
    mMaterialColors.add("#76ff03");
    mMaterialColors.add("#64dd17");

    // Lime
    mMaterialColors.add("#cddc39");
    mMaterialColors.add("#f9fbe7");
    mMaterialColors.add("#f0f4c3");
    mMaterialColors.add("#e6ee9c");
    mMaterialColors.add("#dce775");
    mMaterialColors.add("#d4e157");
    mMaterialColors.add("#cddc39");
    mMaterialColors.add("#c0ca33");
    mMaterialColors.add("#afb42b");
    mMaterialColors.add("#9e9d24");
    mMaterialColors.add("#827717");
    mMaterialColors.add("#f4ff81");
    mMaterialColors.add("#eeff41");
    mMaterialColors.add("#c6ff00");
    mMaterialColors.add("#aeea00");

    // Yellow
    mMaterialColors.add("#ffeb3b");
    mMaterialColors.add("#fffde7");
    mMaterialColors.add("#fff9c4");
    mMaterialColors.add("#fff59d");
    mMaterialColors.add("#fff176");
    mMaterialColors.add("#ffee58");
    mMaterialColors.add("#ffeb3b");
    mMaterialColors.add("#fdd835");
    mMaterialColors.add("#fbc02d");
    mMaterialColors.add("#f9a825");
    mMaterialColors.add("#f57f17");
    mMaterialColors.add("#ffff8d");
    mMaterialColors.add("#ffff00");
    mMaterialColors.add("#ffea00");
    mMaterialColors.add("#ffd600");

    // Amber
    mMaterialColors.add("#ffc107");
    mMaterialColors.add("#fff8e1");
    mMaterialColors.add("#ffecb3");
    mMaterialColors.add("#ffe082");
    mMaterialColors.add("#ffd54f");
    mMaterialColors.add("#ffca28");
    mMaterialColors.add("#ffc107");
    mMaterialColors.add("#ffb300");
    mMaterialColors.add("#ffa000");
    mMaterialColors.add("#ff8f00");
    mMaterialColors.add("#ff6f00");
    mMaterialColors.add("#ffe57f");
    mMaterialColors.add("#ffd740");
    mMaterialColors.add("#ffc400");
    mMaterialColors.add("#ffab00");

    // Orange
    mMaterialColors.add("#ff9800");
    mMaterialColors.add("#fff3e0");
    mMaterialColors.add("#ffe0b2");
    mMaterialColors.add("#ffcc80");
    mMaterialColors.add("#ffb74d");
    mMaterialColors.add("#ffa726");
    mMaterialColors.add("#ff9800");
    mMaterialColors.add("#fb8c00");
    mMaterialColors.add("#f57c00");
    mMaterialColors.add("#ef6c00");
    mMaterialColors.add("#e65100");
    mMaterialColors.add("#ffd180");
    mMaterialColors.add("#ffab40");
    mMaterialColors.add("#ff9100");
    mMaterialColors.add("#ff6d00");

    // Deep Orange
    mMaterialColors.add("#ff5722");
    mMaterialColors.add("#fbe9e7");
    mMaterialColors.add("#ffccbc");
    mMaterialColors.add("#ffab91");
    mMaterialColors.add("#ff8a65");
    mMaterialColors.add("#ff7043");
    mMaterialColors.add("#ff5722");
    mMaterialColors.add("#f4511e");
    mMaterialColors.add("#e64a19");
    mMaterialColors.add("#d84315");
    mMaterialColors.add("#bf360c");
    mMaterialColors.add("#ff9e80");
    mMaterialColors.add("#ff6e40");
    mMaterialColors.add("#ff3d00");
    mMaterialColors.add("#dd2c00");

    // Brown
    mMaterialColors.add("#795548");
    mMaterialColors.add("#efebe9");
    mMaterialColors.add("#d7ccc8");
    mMaterialColors.add("#bcaaa4");
    mMaterialColors.add("#a1887f");
    mMaterialColors.add("#8d6e63");
    mMaterialColors.add("#795548");
    mMaterialColors.add("#6d4c41");
    mMaterialColors.add("#5d4037");
    mMaterialColors.add("#4e342e");
    mMaterialColors.add("#3e2723");

    // Grey
    mMaterialColors.add("#9e9e9e");
    mMaterialColors.add("#fafafa");
    mMaterialColors.add("#f5f5f5");
    mMaterialColors.add("#eeeeee");
    mMaterialColors.add("#e0e0e0");
    mMaterialColors.add("#bdbdbd");
    mMaterialColors.add("#9e9e9e");
    mMaterialColors.add("#757575");
    mMaterialColors.add("#616161");
    mMaterialColors.add("#424242");
    mMaterialColors.add("#212121");
    mMaterialColors.add("#000000");
    mMaterialColors.add("#ffffff");

    // Blue Grey
    mMaterialColors.add("#607d8b");
    mMaterialColors.add("#eceff1");
    mMaterialColors.add("#cfd8dc");
    mMaterialColors.add("#b0bec5");
    mMaterialColors.add("#90a4ae");
    mMaterialColors.add("#78909c");
    mMaterialColors.add("#607d8b");
    mMaterialColors.add("#546e7a");
    mMaterialColors.add("#455a64");
    mMaterialColors.add("#37474f");
    mMaterialColors.add("#263238");

  }


  fun randomColor(): Int {
    val randomGenerator = Random()
    val randomIndex = randomGenerator.nextInt(mMaterialColors.size)

    return Color.parseColor(mMaterialColors[randomIndex])
  }


}