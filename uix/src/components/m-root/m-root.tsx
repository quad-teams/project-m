import { MDCDrawer } from '@material/drawer';
import { MDCTopAppBar } from '@material/top-app-bar';
import { Component, Element } from '@stencil/core';

@Component({
  tag: 'm-root',
  styleUrl: 'm-root.scss',
})
export class MRoot {

  @Element()
  element: Element;

  componentDidLoad() {
    const drawer = MDCDrawer.attachTo(document.querySelector('.mdc-drawer'));
    const bar = MDCTopAppBar.attachTo(document.getElementById('app-bar'));
    bar.setScrollTarget(document.getElementById('main-content'));
    bar.listen('MDCTopAppBar:nav', () => {
      drawer.open = !drawer.open;
    });
  }

  showUploadModal() {
    const uploadModal: any = this.element.querySelector('m-upload-modal');
    uploadModal.open();
  }

  render() {
    return (
      <div class="app-root">
        <header class="mdc-top-app-bar" id="app-bar">
          <div class="mdc-top-app-bar__row">
            <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-start">
              <a href="#" class="material-icons mdc-top-app-bar__navigation-icon">menu</a>
              <span class="mdc-top-app-bar__title">Project M</span>
            </section>
            <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-end" role="toolbar">
              <a href="#" class="material-icons mdc-top-app-bar__action-item" aria-label="Upload"
                 onClick={() => this.showUploadModal()}>cloud_upload</a>

              <a href="#" class="material-icons mdc-top-app-bar__action-item" aria-label="Profile">person</a>
            </section>
          </div>
        </header>

        <aside class="mdc-drawer mdc-drawer--modal">
          <div class="mdc-drawer__content">
            <nav class="mdc-list">
              <a class="mdc-list-item mdc-list-item--activated" href="#" aria-current="page">
                <i class="material-icons mdc-list-item__graphic" aria-hidden="true">help</i>
                <span class="mdc-list-item__text">Help</span>
              </a>
            </nav>
          </div>
        </aside>

        <div class="mdc-drawer-scrim"></div>
        <div class="mdc-drawer-app-content mdc-top-app-bar--fixed-adjust">
          <main class="main-content" id="main-content">
            <div class="main-content-wrapper" id="main-content-wrapper">
              <stencil-router>
                <stencil-route-switch scrollTopOffset={0}>
                  <stencil-route url='/' component='m-dashboard' exact={true}/>
                </stencil-route-switch>
              </stencil-router>
            </div>
          </main>
        </div>

        {/*Modals*/}
        <m-upload-modal/>
      </div>
    );
  }
}
