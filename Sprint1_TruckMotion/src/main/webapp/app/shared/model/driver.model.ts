import { ITruck } from 'app/shared/model/truck.model';
import { IUser } from 'app/shared/model/user.model';

export interface IDriver {
  id?: string;
  truck?: ITruck | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IDriver> = {};
