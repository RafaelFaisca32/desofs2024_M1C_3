import { IUser } from 'app/shared/model/user.model';

export interface ICustomer {
  id?: string;
  company?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ICustomer> = {};
