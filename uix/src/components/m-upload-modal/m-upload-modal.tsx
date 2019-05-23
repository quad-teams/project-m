import { MDCDialog } from '@material/dialog';
import '@material/top-app-bar';
import { Component, Method } from '@stencil/core';
import Dropzone from 'dropzone';

@Component({
  tag: 'm-upload-modal',
  styleUrl: 'm-upload-modal.scss'
})
export class MUploadModal {

  private dialog: MDCDialog;
  private dropzone: Dropzone;

  componentDidLoad() {
    this.dialog = new MDCDialog(document.querySelector('.mdc-dialog'));
    this.dropzone = new Dropzone('.dropzone', {
      url: 'https://hookb.in/wNORjE0VXph0w0V3B3oR',
    });
  }

  @Method()
  open() {
    this.dropzone.removeAllFiles(true);
    this.dialog.open();
  }

  render() {
    return (
      <div class="m-upload-modal mdc-dialog">
        <div class="mdc-dialog__container">
          <div class="mdc-dialog__surface">
            <h2 class="mdc-dialog__title">Import files</h2>
            <div class="mdc-dialog__content">
              <div class="dropzone"></div>
            </div>
            <footer class="mdc-dialog__actions">
              <button type="button" class="mdc-button mdc-dialog__button" data-mdc-dialog-action="close">
                <span class="mdc-button__label">Close</span>
              </button>
            </footer>
          </div>
        </div>
        <div class="mdc-dialog__scrim"></div>
      </div>
    );
  }
}
