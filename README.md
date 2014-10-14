`-*- mode: markdown; mode: visual-line; mode: adaptive-wrap-prefix; -*-`

reveal-js-render-clj
====================

A renderer for turning Hiccup-based presentation files into `reveal.js` presentations.

Our [forked copy](https://github.com/cassiel/reveal.js) of `reveal.js` contains an extra file: an [index file](https://github.com/cassiel/reveal.js/blob/master/index-ssi.shtml) where the slide content has been removed and replaced with [SSI](http://en.wikipedia.org/wiki/Server_Side_Includes) directives to include content made with some other HTML generator (as we've done with our [demo slides](https://github.com/cassiel/reveal-js-demo-slides)). This is fine for one-off presentation rendering, for a server with SSI enabled, but is less good for releasing standalone presentation sets in HTML which can be distributed and viewed directly via `file:` URLs.

This project uses the index file from our `reveal.js` fork (which must be available locally) and renders static HTML.

## Usage

You need an installed Java system, and [Leiningen](http://leiningen.org/). Then, in the root folder of this project:

        lein run --reveal.js <where our reveal.js is installed> \
                 --dir <enclosing directory for presentations> \
                 --input presentation.clj \
                 --output presentation.html
                      
The `--reveal.js` argument defaults to `~/GITHUB/cassiel/reveal.js/`.

*Warning*: note that the input file is actually evaluated as a Clojure program (we use `read` and `eval`), so it should only contain code that you trust. (We have partial support for EDN, but it's not clear how useful this would be in isolation.)

Another warning: note that the `reveal.js` support directories (`js`, `lib`, `plugin`, `css`) are copied into place alongside the output, so existing directories with these names will be removed.

The presentation file takes a particular form: a call to a function `render` with keyword arguments for (CSS) `:theme`, `:title`, `:author` and `:slides` (a sequence of `:section` blocks). The output is a complete HTML document with HEAD and BODY properly populated, constructed from the index file of our `reveal.js` project.

There's an [example slide set](example/example_slides.clj) in the directory `example`. To render that:

        lein run --dir example \
                 --input example_slides.clj \
                 --output example_slides.html

(assuming a `reveal.js` in the expected location). Then open the resulting `example_slides.html` in a browser.

## Helper Functions

The `example_slides` set contains examples of all the helper functions available (these are injected into the namespace of the presentation file, as is the top-level `render` function). The helpers are:

- `(code ...)`: add monospaced code block, passed as explicit strings

- `(include-code <file>)`: include a file from the `include` subdirectory as a code block

- `(link <url>)`: link to a URL, with the URL as text, monospaced (in the more general case, use Hiccup's `[:a {:href ...} ...]`)

- `(image-h <height> <image>)`: include an image from a file in the `images` subdirectory, with the specified height

- `(image <image>)`: include an image with default height

- `(youtube-link <id> <text>)`: include a link to a YouTube video

- `(vimeo-link <id> <text>)`: include a link to a Vimeo video

- `(youtube-embed-h <height> <id> <text>)`: embed a YouTube video, with specified height (and 16:9 aspect ratio)

- `(youtube-embed <id> <text>)`: embed a YouTube video, with default height

- `(vimeo-embed-h <height> <id> <text>)`: embed a Vimeo video, with specified height (and 16:9 aspect ratio)

- `(vimeo-embed <id> <text>)`: embed a Vimeo video, with default height

- `(github <path> <text>)`: include a link to a GitHub project.

## Performance

Spinning up a JVM is expensive - it can take quite a few seconds to load, which is irritating when doing quick tweaks to a presentation. If you have some Clojure experience and can drive a REPL, look at `from-repl.clj`, which contains an example of a direct Clojure call to run the render process. This should take a fraction of a second.

## License

Copyright © 2014 Nick Rothwell

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
