import { ITruck } from 'app/shared/model/truck.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IDriver {
  id?: string;
  truck?: ITruck | null;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<IDriver> = {};
