import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Manager from './manager';
import ManagerDetail from './manager-detail';
import ManagerUpdate from './manager-update';
import ManagerDeleteDialog from './manager-delete-dialog';

const ManagerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Manager />} />
    <Route path="new" element={<ManagerUpdate />} />
    <Route path=":id">
      <Route index element={<ManagerDetail />} />
      <Route path="edit" element={<ManagerUpdate />} />
      <Route path="delete" element={<ManagerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ManagerRoutes;
