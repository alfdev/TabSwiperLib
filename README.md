Android TabSwiperLib.
============================================

Simple ViewPager extension with animated paging indicator.  
Compatible since API Level 9.

Developed by
---

Alfredo De Vito - <alfdev@gmail.com>

Usage
---

1.  Include, in your view, the TabSwiper xml definition:  
      `<net.alfdev.android.tabswiperlib.TabSwiper
        android:id="@+id/tabSwiper1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_alignParentLeft="true" />`

2.  In your Activity:  
       `ts = (TabSwiper) findViewById(R.id.tabSwiper1);`  
       `adapter = new MyAdapter(getSupportFragmentManager());`  
       `ts.setAdapter(adapter);`  

     Where `MyAdapter` is a PagerAdapter that extends `FragmentStatePagerAdapter`.

*You found a complete implementation in sample folder .*  

License
=======  

    Copyright 2013 Alfredo De vito

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.