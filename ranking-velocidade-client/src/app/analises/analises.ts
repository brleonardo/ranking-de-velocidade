import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { Router } from '@angular/router';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

@Component({
  selector: 'app-analises',
  imports: [MatCardModule, MatIconModule, MatTableModule],
  templateUrl: './analises.html',
  styleUrl: './analises.scss',
})
export class Analises {
  colunasRanking: string[] = ['Gravidade', 'Placa'];
  colunasEpisodios: string[] = ['Gravidade', 'Placa', 'Inicio', 'Fim', 'Duracao', 'VelocidadeMaxima', 'ExcessoMaximo', 'Leituras'];

  dataSource;

  private router = inject(Router);

  constructor() {
    const navigation = this.router.currentNavigation();

    const response = navigation?.extras.state?.['data'];

    if (response) {
      console.log(response);
      this.dataSource = response;
    }
  }
}
