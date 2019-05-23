import { MDCDrawer } from '@material/drawer';
import { MDCTopAppBar } from '@material/top-app-bar';
import { Component } from '@stencil/core';


@Component({
  tag: 'app-root',
  styleUrl: 'app-root.scss',
})
export class AppRoot {

  componentDidLoad() {
    const drawer = MDCDrawer.attachTo(document.querySelector('.mdc-drawer'));
    const topAppBar = MDCTopAppBar.attachTo(document.getElementById('app-bar'));

    topAppBar.setScrollTarget(document.getElementById('main-content'));
    topAppBar.listen('MDCTopAppBar:nav', () => {
      drawer.open = !drawer.open;
    });
  }

  render() {
    return (
      <div>
        <header class="mdc-top-app-bar app-bar" id="app-bar">
          <div class="mdc-top-app-bar__row">
            <section class="mdc-top-app-bar__section mdc-top-app-bar__section--align-start">
              <a href="#" class="demo-menu material-icons mdc-top-app-bar__navigation-icon">menu</a>
              <span class="mdc-top-app-bar__title">Dismissible Drawer</span>
            </section>
          </div>
        </header>
        <aside class="mdc-drawer mdc-drawer--modal">
          <div class="mdc-drawer__content">
            <nav class="mdc-list">
              <a class="mdc-list-item mdc-list-item--activated" href="#" aria-current="page">
                <i class="material-icons mdc-list-item__graphic" aria-hidden="true">inbox</i>
                <span class="mdc-list-item__text">Inbox</span>
              </a>
              <a class="mdc-list-item" href="#">
                <i class="material-icons mdc-list-item__graphic" aria-hidden="true">send</i>
                <span class="mdc-list-item__text">Outgoing</span>
              </a>
              <a class="mdc-list-item" href="#">
                <i class="material-icons mdc-list-item__graphic" aria-hidden="true">drafts</i>
                <span class="mdc-list-item__text">Drafts</span>
              </a>
            </nav>
          </div>
        </aside>

        <div class="mdc-drawer-scrim"></div>
        <div class="mdc-drawer-app-content mdc-top-app-bar--fixed-adjust">
          <main class="main-content" id="main-content">
            <stencil-router>
              <stencil-route-switch scrollTopOffset={0}>
                <stencil-route url='/' component='app-home' exact={true}/>
                <stencil-route url='/profile/:name' component='app-profile'/>
              </stencil-route-switch>
            </stencil-router>
          </main>
        </div>
      </div>
    );
  }
}
