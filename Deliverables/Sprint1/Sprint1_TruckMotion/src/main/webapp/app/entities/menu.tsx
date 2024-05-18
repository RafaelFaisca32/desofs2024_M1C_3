import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/driver">
        Driver
      </MenuItem>
      <MenuItem icon="asterisk" to="/manager">
        Manager
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        Customer
      </MenuItem>
      <MenuItem icon="asterisk" to="/application-user">
        Application User
      </MenuItem>
      <MenuItem icon="asterisk" to="/truck">
        Truck
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        Location
      </MenuItem>
      <MenuItem icon="asterisk" to="/service-request">
        Service Request
      </MenuItem>
      <MenuItem icon="asterisk" to="/transport">
        Transport
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
