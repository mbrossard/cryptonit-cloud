import AppLayout from '../layouts/AppLayout'
import Home from './Home'

export const createRoutes = (store) => ({
  path        : '/',
  component   : AppLayout,
  indexRoute  : Home,
  childRoutes : [
  ]
})

export default createRoutes
