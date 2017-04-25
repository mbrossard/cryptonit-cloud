import MainLayout from '../layouts/MainLayout';
import Home from './Home';
import Routes from './routes';

export const createRoutes = (store) => ({
    path: '/',
    component: MainLayout,
    indexRoute: Home,
    childRoutes: Routes
});

export default createRoutes
