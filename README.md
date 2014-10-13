`-*- mode: markdown; mode: visual-line; mode: adaptive-wrap-prefix; -*-`

reveal-js-render-clj
====================

A renderer for turning Hiccup-based presentation files into reveal.js presentations.

Our [forked copy](https://github.com/cassiel/reveal.js) of `reveal.js` contains an extra file: an [index file](https://github.com/cassiel/reveal.js/blob/master/index-ssi.shtml) where the slide content has been removed and replaced with SSI directives to include content made with some other HTML generator (as we've done with our [demo slides](https://github.com/cassiel/reveal-js-demo-slides)). This is fine for one-off presentation rendering, for a server with SSI enabled, but is less good for releasing standalone presentation sets in HTML which can be viewed directly via `file:` URLs.

This project uses the index file from our `reveal.js` fork (which must be available locally) and renders static HTML.

## Usage

You need an installed Java system, and [Leiningen](http://leiningen.org/). Then, in the root folder of this project:

        lein run --reveal.js <where our reveal.js is installed> \
                 --dir <enclosing directory for presentations> \
                 --input presentation.clj \
                 --output presentation.html
                      
The `--reveal.js` argument defaults to `~/GITHUB/cassiel/reveal.js/`. *Warning*: note that the input file is actually evaluated as a Clojure program (we use `read` and `eval`), so it should only contain code that you trust. (We have partial support for EDN, but it's not clear how useful this would be in isolation.)

Another warning: note that the `reveal.js` support directories (`js`, `lib`, `plugin`, `css`) are copied into place alongside the output, so existing directories with these names will be removed.

## License

Copyright © 2014 Nick Rothwell

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
