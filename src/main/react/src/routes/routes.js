export default [
    {
        path: '/timestamping',
        /*  Async WebPack code split  */
        getComponent: (nextState, cb) => {
            require.ensure([], require => {
                cb(null, require('./Apps/Timestamping').default);
            }, 'timestamping');
        }
    },
];
