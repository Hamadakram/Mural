# Mural
A lightweight image loading library in Kotlin

![](https://github.com/Hamadakram/Mural/blob/master/art/banner.jpg?raw=true)
## Download
Grab via Gradle:
```java
implementation 'com.irozon.mural:mural:1.0.1'
```
## Usage
#### Using Kotlin Extensions
```java
imageView.placeholder = resources.getDrawable(R.drawable.placeholder)
imageView.source = imageUrl // Url or drawable
```
#### Using Mural Builder
```java
Mural.with(this)
     .placeholder(R.drawable.placeholder)
     .source(imageUrl)
     .loadImage(imageView)
```

##### Other options
```java
Mural.with(this)
     .placeholder(R.drawable.placeholder)
     .resize(300, 300) // Resize image
     .disableCache() // Disable cache. By default its enabled
     .source(imageUrl)
     .loadImage(imageView)
```
##### Placeholder
Extension: To use color as placeholder, use `ColorDrawable`
```java
imageView.placeholder = ColorDrawable(resources.getColor(R.color.colorAccent))
```
Builder:
```java
Mural.with(this)
     .placeholder(R.color.colorAccent)
     ...
```
## Authors

* **Hammad Akram** - (https://github.com/hamadakram)

## Contribution
Pull requests are welcome! Feel free to browse through open issues to look for things that need work. If you have a feature request or bug, please open a new issue so i can track it.
## Licence
```
Copyright 2018 Irozon, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
