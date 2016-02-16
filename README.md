# New York Times Article Search

# Pre-work - New York Times Article Search

Build a New York Times News Search article app which allows a user to find old articles.

Submitted by: Luiza Carneiro

Time spent: Not sure

## User Stories

The following user stories must be completed:

* [x] User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API. (3 points)
* [x]User can click on "settings" which allows selection of advanced search options to filter results. (3 points)
* [x]User can configure advanced search filters such as: (points included above)
Begin Date (using a date picker)
News desk values (Arts, Fashion & Style, Sports)
Sort order (oldest or newest)
* [x] Subsequent searches will have any filters applied to the search results. (1 point)
* [x] User can tap on any article in results to view the contents in an embedded browser. (2 points)
* [x] User can scroll down "infinitely" to continue loading more news articles. The maximum number of articles is limited by the API search. (1 point)

The following advanced user stories are optional but recommended:

* [] Advanced: Robust error handling, check if internet is available, handle error cases, network failures. (1 point)
* [] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText. (1 point)
* [] Advanced: User can share a link to their friends or email it to themselves. (1 point)
* [] Advanced: Replace Filter Settings Activity with a lightweight modal overlay. (2 points)
* [] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring (1 to 3 points depending on the difficulty of UI improvements)
* [] Bonus: Use the RecyclerView with the StaggeredGridLayoutManager to display improve the grid of image results (see Picasso guide too). (2 points)
* [] Bonus: For different news articles that only have text or have text with thumbnails, use Heterogenous Layouts with RecyclerView. (2 points)
* [] Bonus: Apply the popular ButterKnife annotation library to reduce view boilerplate. (1 point)
* [] Bonus: Use Parcelable instead of Serializable using the popular Parceler library. (1 point)
* [] Bonus: Leverage the popular GSON library to streamline the parsing of JSON data. (1 point)
* [] Bonus: Replace Picasso with Glide for more efficient image rendering. (1 point)

## Additional User Stories
* [x] If not thumbnail for the article is available, display the NYTimes Logo

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<a href="http://i.imgur.com/BtAG7yQ.gifv" target="_blank">Video Walkthrough</a>

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes
* 
.

## License

    Copyright [2016] [Luiza Carneiro]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
