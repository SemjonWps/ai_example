import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';  // Import HttpClientModule
import { AppComponent } from './app.component';  // Import your root component

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, HttpClientModule],  // Add HttpClientModule here
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
