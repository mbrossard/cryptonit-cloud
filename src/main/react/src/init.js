(function() {
     var be = document.querySelector('body');
     be.classList.add('loading');
     document.addEventListener('readystatechange', function() {
         if(document.readyState === 'complete') {
             var be = document.querySelector('body');
             be.classList.add('loaded');
             setTimeout(function() {
                 be.removeChild(document.querySelector('#initial-loader'));
                 be.classList.remove('loading', 'loaded');
             }, 250);
         }
     });
 })();
