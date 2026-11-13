import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Analises } from './analises';

describe('Analises', () => {
  let component: Analises;
  let fixture: ComponentFixture<Analises>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Analises],
    }).compileComponents();

    fixture = TestBed.createComponent(Analises);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
