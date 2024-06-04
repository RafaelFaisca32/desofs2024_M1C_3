import { IApplicationUser } from 'app/shared/model/application-user.model';
import {ILocation} from "app/shared/model/location.model";

export interface ICustomer {
  id?: string;
  company?: string | null;
  applicationUser?: IApplicationUser | null;
  locations?: ILocation[] | null;
}

export const defaultValue: Readonly<ICustomer> = {};
