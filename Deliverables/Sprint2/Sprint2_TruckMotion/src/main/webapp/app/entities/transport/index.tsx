import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Transport from './transport';
import TransportDetail from './transport-detail';

const TransportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Transport />} />
    <Route path=":id">
      <Route index element={<TransportDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TransportRoutes;
