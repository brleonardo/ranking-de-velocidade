import { Routes } from '@angular/router';
import { Upload } from './upload/upload';
import { Analises } from './analises/analises';


export const routes: Routes = [
    {
        path: '',
        component: Upload
    },
    {
        path: 'analises',
        component: Analises
    }
];
