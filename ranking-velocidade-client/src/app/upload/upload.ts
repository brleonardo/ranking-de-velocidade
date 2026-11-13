import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { UploadService } from '../services/upload';

@Component({
  selector: 'app-upload',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './upload.html',
  styleUrl: './upload.scss',
})
export class Upload {
  private uploadService = inject(UploadService);

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
      },
      error: (error) => {
        console.error('Upload failed!', error);
      }
    });
  }
}
