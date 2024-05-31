import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IManager {
  id?: string;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<IManager> = {};
