`-*- word-wrap: t; -*-`

reveal.js-render-clj
====================

A renderer for turning Hiccup-based presentation files into reveal.js presentations.

## Usage

You need an installed Java system. Then:

        java xxxx.jar --reveal.js <where-our-reveal.js-is-installed> \
                      --input presentation.clj \
                      --output presentation.html
                      
The `--reveal.js` argument defaults to `~/GITHUB/cassiel/reveal.js/`. The `--output` argument defaults to the same name as the input, with `.clj` replaced by `.html`.

Note that the `reveal.js` support directories (`js` and `lib`) are copied into place alongside the output, so existing directories with these names will be removed.

## License

Copyright © 2014 Nick Rothwell

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
