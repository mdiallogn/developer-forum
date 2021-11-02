import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessagetestComponent } from './messagetest.component';

describe('MessagetestComponent', () => {
  let component: MessagetestComponent;
  let fixture: ComponentFixture<MessagetestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessagetestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessagetestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
