import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { useAppSelector } from '../config/store';
import { AUTHORITIES } from '../config/constants';

const EntitiesMenu = () => {
  const account = useAppSelector(state => state.authentication.account);
  const authorities = account.authorities as string[]
  const managerAndAdmin = [AUTHORITIES.MANAGER, AUTHORITIES.ADMIN];
  const managerAdminDriver = [AUTHORITIES.MANAGER, AUTHORITIES.ADMIN, AUTHORITIES.DRIVER];
  const managerAdminCostumer = [AUTHORITIES.MANAGER, AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER];
  const adminAndDriver = [AUTHORITIES.DRIVER, AUTHORITIES.ADMIN];
  return (
    <>
      {managerAndAdmin.some(value => authorities.includes(value)) ?
          <><MenuItem icon="asterisk" to="/driver">
            Driver
          </MenuItem><MenuItem icon="asterisk" to="/manager">
            Manager
          </MenuItem><MenuItem icon="asterisk" to="/customer">
            Customer
          </MenuItem></>
      : null}
      {managerAdminCostumer.some(value => authorities.includes(value)) ?
        <><MenuItem icon="asterisk" to="/location">
          Location
        </MenuItem><MenuItem icon="asterisk" to="/service-request">
            Service Request
        </MenuItem></>
      : null}
      {managerAdminDriver.some(value => authorities.includes(value)) ?
        <MenuItem icon="asterisk" to="/truck">
          Truck
        </MenuItem>
      : null}
      {adminAndDriver.some(value => authorities.includes(value)) ?
      <MenuItem icon="asterisk" to="/transport">
        Transport
      </MenuItem>
      : null}

      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
