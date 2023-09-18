//
//  MainTabView.swift
//  catchnl
//
//  Created by Fatih Toker on 13.09.2023.
//

import SwiftUI

struct MainTabView: View {
    @Environment(\.colorScheme) var isDark
    @EnvironmentObject var viewModel: ContentViewModel
    @State var tabSelection = 1
    
    var body: some View {
        TabView(selection: $tabSelection) {
            NotificationView(user: viewModel.currentUser)
                .tabItem {
                    Label("Meldingen", systemImage: "bell")
                }
                .tag(0)
            
            HomeView()
                .tabItem {
                    Label("Anasayfa", systemImage: "house")
                }
                .tag(1)
            
            ProfileView()
                .tabItem {
                    Label("Profil", systemImage: "person")
                }
                .tag(2)
        }
        .accentColor(Color("primary-color"))
    }
}

struct MainTabView_Previews: PreviewProvider {
    static var previews: some View {
        MainTabView()
    }
}
