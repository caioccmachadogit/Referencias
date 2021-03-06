#LazyFlickr

LazyFlickr is a sample Android application that shows images from Flickr based on a location feed.  The application utilizes a Lazy Loading technique that also caches data to the device's SD card for later retrieval.

![https://lh6.googleusercontent.com/-6lWLu9fr_mI/UKsu5bCFa5I/AAAAAAAAEA0/QKmsCHoR3JM/s512/Screenshot_2012-11-20-02-14-58.png](https://lh6.googleusercontent.com/-6lWLu9fr_mI/UKsu5bCFa5I/AAAAAAAAEA0/QKmsCHoR3JM/s512/Screenshot_2012-11-20-02-14-58.png)
![https://lh3.googleusercontent.com/-X_TjN18sQVI/UKmmEJSBUuI/AAAAAAAAEAk/5mFF7QWGhTo/s512/Screenshot_2012-11-18-22-22-30.png](https://lh3.googleusercontent.com/-X_TjN18sQVI/UKmmEJSBUuI/AAAAAAAAEAk/5mFF7QWGhTo/s512/Screenshot_2012-11-18-22-22-30.png)
![https://lh6.googleusercontent.com/-pBSBG4TS_YI/UKml-UfbflI/AAAAAAAAEAc/dqI5n4DyJdY/s512/Screenshot_2012-11-18-16-36-41.png](https://lh6.googleusercontent.com/-pBSBG4TS_YI/UKml-UfbflI/AAAAAAAAEAc/dqI5n4DyJdY/s512/Screenshot_2012-11-18-16-36-41.png)
![https://lh3.googleusercontent.com/-ZGviJWLz-k0/UKsu-FFT4cI/AAAAAAAAEBU/LkcxJFFsPo8/s512/Screenshot_2012-11-20-02-17-11.png](https://lh3.googleusercontent.com/-ZGviJWLz-k0/UKsu-FFT4cI/AAAAAAAAEBU/LkcxJFFsPo8/s512/Screenshot_2012-11-20-02-17-11.png)

## Proposed Feature Set

Display a scrolling list of images loaded from [http://api.flickr.com/services/feeds/photos_public.gne?tags=boston](http://api.flickr.com/services/feeds/photos_public.gne?tags=boston).  Asynchronously load the photos if they're not in the cache, add them to the cache, and make them available for display.  The cache should be on disk so that the photos are available in the future launches of the application.

## Notable Features

 - Search for any set of tags on Flickr!
 - Full offline support for multiple feed data streams as well as image data
    - Supports, and uses in a tiered fashion, memory and then disk caching
    - Features Cache preferences and adjustable load options
 - A gallery implementation using ViewPager
 - ActionBar implemented using ActionBarSherlock 

### UI Fun

 - Asynchronous, Crossfading image transitions upon image load
 - Custom animated refresh icon instead of indeterminate progress bar
 - Consistently scaled thumbnails in density based pixel coordinates created from Flickr's 75x75px thumbnails

## TODO

**Features**  

 - The app is running low on memory when dealing with full size images.  Adjust the storage and decoding of images to store lower resolution versions of images based on the screen's size.
 - Apply a small ~5px dark grey border around thumbnail images
 - Create an option in the ActionBar to save the current image

**Bugs**  

 - Large images will crossfade to blank with internet off