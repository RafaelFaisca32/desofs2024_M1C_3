import {IApplicationUser} from "app/shared/model/application-user.model";
import {IManager} from "app/shared/model/manager.model";
import {IDriver} from "app/shared/model/driver.model";
import {ICustomer} from "app/shared/model/customer.model";

export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  createdBy?: string;
  createdDate?: Date | null;
  lastModifiedBy?: string;
  lastModifiedDate?: Date | null;
  password?: string;
}

export interface ICompleteUser {
  userDTO: IUser,
  applicationUserDTO: IApplicationUser,
  managerDTO?: IManager,
  driverDTO?: IDriver,
  customerDTO? : ICustomer
}

export const defaultValue: Readonly<IUser> = {
  id: '',
  login: '',
  firstName: '',
  lastName: '',
  email: '',
  activated: true,
  langKey: '',
  authorities: [],
  createdBy: '',
  createdDate: null,
  lastModifiedBy: '',
  lastModifiedDate: null,
  password: '',
};
