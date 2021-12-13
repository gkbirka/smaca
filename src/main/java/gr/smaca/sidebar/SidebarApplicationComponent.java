package gr.smaca.sidebar;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;

public class SidebarApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        SidebarViewModel viewModel = new SidebarViewModel(context.getEventBus());
        SidebarView view = new SidebarView(viewModel);
        context.getContainer().setLeft(view.load());
    }
}