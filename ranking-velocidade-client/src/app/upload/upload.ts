import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { UploadService } from '../services/upload';
import { Router } from '@angular/router';

@Component({
  selector: 'app-upload',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './upload.html',
  styleUrl: './upload.scss',
})
export class Upload {
  private uploadService = inject(UploadService);
  private router = inject(Router);

  arquivo: File | null = null;

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files?.length) {
      this.arquivo = input.files[0];
      this.upload();
    }
  }

  upload(): void {
    if (!this.arquivo) return;

    this.uploadService.uploadArquivo(this.arquivo).subscribe({
      next: (response) => {
        console.log('Upload successful!', response);
        this.router.navigate(['/analises'], {
          state: {
            data: response
          }
        });
      },
      error: (error) => {
        console.error('Upload failed!', error);
      }
    });
  }
}
