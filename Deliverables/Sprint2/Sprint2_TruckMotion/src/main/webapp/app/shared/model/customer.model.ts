import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface ICustomer {
  id?: string;
  company?: string | null;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<ICustomer> = {};
