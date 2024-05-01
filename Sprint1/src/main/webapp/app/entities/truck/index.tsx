import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Truck from './truck';
import TruckDetail from './truck-detail';
import TruckUpdate from './truck-update';
import TruckDeleteDialog from './truck-delete-dialog';

const TruckRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Truck />} />
    <Route path="new" element={<TruckUpdate />} />
    <Route path=":id">
      <Route index element={<TruckDetail />} />
      <Route path="edit" element={<TruckUpdate />} />
      <Route path="delete" element={<TruckDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TruckRoutes;
