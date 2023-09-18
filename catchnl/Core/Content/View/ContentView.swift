//
//  ContentView.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var viewModel: ContentViewModel
    
    var body: some View {
        
        switch viewModel.state {
            case .noWifi:
                NoInternetView()
            
            case .info:
                InfoView()
            
            case .splash:
                SplashView()
            
            case .notAccepted:
                NotAcceptedView()
            
            case .registerView:
                RegisterView()
                
            case .loginView:
                LoginView()
                
            case .main:
                MainTabView()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
