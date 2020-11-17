import { inject, TestBed } from '@angular/core/testing';
import {AssistComponent} from './assist.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';


describe('AssistComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AssistComponent]
    });
  });

  it('Should be created', inject([AssistComponent], (service: AssistComponent) => {
    expect(service).toBeTruthy();
  }));

  it('Should have areAllOptionsSelected() function', inject([AssistComponent], (service: AssistComponent) => {
    expect(service.areAllOptionsSelected).toBeTruthy();
  }));

  it('Should have getItemsForDropDown function', inject([AssistComponent], (service: AssistComponent) => {
    expect(service.getItemsForDropDown).toBeTruthy();
  }));

  it('Should return true', inject([AssistComponent], (service: AssistComponent) => {
      service.touchscreen = 'Yes';
      service.transportMonitor = 'Yes';
      service.category = 'Intellivue';
      service.size = 10;
      expect(service.areAllOptionsSelected()).toEqual(true);
    }));

  it('Should return false', inject([AssistComponent], (service: AssistComponent) => {
    expect(service.areAllOptionsSelected()).toEqual(false);
  }));

  });
